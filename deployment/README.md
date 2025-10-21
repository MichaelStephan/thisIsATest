# Kubernetes Payroll Management System

This project contains the necessary Kubernetes configuration files to deploy the Payroll Management System application. The application is packaged as a Docker image and can be deployed in a Kubernetes cluster.

## Project Structure

The project has the following files:

- **k8s/namespace.yaml**: Defines a Kubernetes namespace for the application, isolating it from other applications in the cluster.
  
- **k8s/deployment.yaml**: Specifies the deployment configuration for the application. It defines the desired state for the application, including the Docker image `michaelstephan/payrollmngmt:0.0.1`, the number of replicas, and the container ports.
  
- **k8s/service.yaml**: Defines a service to expose the application. It specifies the type of service (e.g., ClusterIP, NodePort) and the ports to be exposed.
  
- **k8s/ingress.yaml**: Configures an ingress resource to manage external access to the application, allowing HTTP and HTTPS traffic to reach the service.
  
- **k8s/hpa.yaml**: Defines a Horizontal Pod Autoscaler to automatically scale the number of pods based on CPU utilization or other select metrics.
  
- **k8s/configmap.yaml**: Creates a ConfigMap to store configuration data that can be consumed by the application pods.
  
- **k8s/kustomization.yaml**: Used for Kustomize, allowing you to manage Kubernetes resources in a more organized way. It references the other YAML files for deployment.

## Deployment Instructions

1. **Set up your Kubernetes cluster**: Ensure you have access to a running Kubernetes cluster.

2. **Create the namespace**: Apply the namespace configuration:
   ```
   kubectl apply -f k8s/namespace.yaml
   ```

3. **Deploy the application**: Apply the deployment configuration:
   ```
   kubectl apply -f k8s/deployment.yaml
   ```

4. **Expose the application**: Apply the service configuration:
   ```
   kubectl apply -f k8s/service.yaml
   ```

5. **Configure ingress (optional)**: If you want to expose the application externally, apply the ingress configuration:
   ```
   kubectl apply -f k8s/ingress.yaml
   ```

6. **Set up Horizontal Pod Autoscaler (optional)**: If you want to enable autoscaling, apply the HPA configuration:
   ```
   kubectl apply -f k8s/hpa.yaml
   ```

7. **Create ConfigMap (if needed)**: Apply the ConfigMap configuration:
   ```
   kubectl apply -f k8s/configmap.yaml
   ```

8. **Use Kustomize (optional)**: If you prefer to use Kustomize, you can run:
   ```
   kubectl apply -k k8s/
   ```

## Accessing the Application

After deployment, you can access the application using the service type defined in `service.yaml`. If you have configured ingress, you can access it via the specified hostname or IP.

## Notes

- Ensure that the Docker image `michaelstephan/payrollmngmt:0.0.1` is available in your container registry.
- Modify the configuration files as needed to suit your environment and requirements.