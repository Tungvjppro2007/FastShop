## 1. Cấu Trúc Thư Mục
k8s-manifest/
├── deployment.yaml   # Khai báo Deployment cho mssql-db (1 pod) và fastshop-app (2 pods)
├── service.yaml      # Khai báo Service ClusterIP cho Database và Web Application
├── ingress.yaml      # Khai báo Ingress định tuyến từ host exam.local vào ứng dụng
├── README.md         # Tài liệu hướng dẫn này
└── report.txt        # Báo cáo kết quả và giải trình yêu cầu bài thi

## 2. Sơ Đồ Hệ Thống

### A. Sơ Đồ Kiến Trúc Truy Cập
    User -->|1. http://exam.local| Ingress[Nginx Ingress Controller]
    Ingress -->|2. Route Host: exam.local| SvcApp[fastshop-service:8000]
    SvcApp -->|3. Load Balancing| PodApp1[fastshop-app-pod-1:8000]
    SvcApp -->|3. Load Balancing| PodApp2[fastshop-app-pod-2:8000]
    PodApp1 & PodApp2 -->|4. Kết nối Database| SvcDB[mssql-service:1433]
    SvcDB -->|5. Forward Port| PodDB[mssql-db-pod:1433]

### B. Sơ Đồ Triển Khai GitOps
    Developer->>Git: Push source code / manifests
    Git-->>CI: Trigger pipeline build
    CI->>CI: Compile Java & Build Docker Image
    CI->>Registry: Push Image (chitung/fastshop-app:latest)
    CI->>Git: Update image tag in deployment.yaml
    ArgoCD->>Git: Polling / Webhook nhận thay đổi
    Note over ArgoCD: Phát hiện trạng thái OutOfSync
    ArgoCD->>K8s: Apply Manifests mới (Sync)
    K8s->>Registry: Pull Image mới từ Registry
    K8s->>K8s: Cập nhật Pods (Rolling Update)
    K8s-->>ArgoCD: Trạng thái Healthy
## 3. Quy Trình Triển Khai Thủ Công

### Bước 1: Khởi động Minikube và kích hoạt Ingress
minikube start
minikube addons enable ingress

### Bước 2: Build Docker Image vào docker daemon của Minikube
eval $(minikube docker-env)
cd ..
./mvnw clean package -DskipTests
docker build -t chitung/fastshop-app:latest .
cd k8s-manifest

### Bước 3: Triển khai manifests vào Kubernetes
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl apply -f ingress.yaml

### Bước 4: Kiểm tra trạng thái tài nguyên
kubectl get deployments
kubectl get pods
kubectl get svc
kubectl get ingress

### Bước 5: Cấu hình phân giải Domain cục bộ (Hosts)
Lấy IP của minikube:
minikube ip

Thêm bản ghi sau vào file hosts của hệ điều hành (`/etc/hosts` trên Linux/macOS hoặc `C:\Windows\System32\drivers\etc\hosts` trên Windows):
<IP_MINIKUBE> exam.local

### Bước 6: Kiểm tra kết quả truy cập
curl http://exam.local
