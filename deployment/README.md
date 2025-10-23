# Kubernetes Payroll Management System

This project contains the necessary Kubernetes configuration files to deploy the Payroll Management System application. The application is packaged as a Docker image and can be deployed in a Kubernetes cluster.

## Project Structure

The project has the following files:

- **k8s/namespace.yaml**: Defines a Kubernetes namespace for the application, isolating it from other applications in the cluster.
  
- **k8s/deployment.yaml**: Specifies the deployment configuration for the application. It defines the desired state for the application, including the Docker image `michaelstephan/payrollmngmt:0.0.1`, the number of replicas, and the container ports.
  
- **k8s/payrollmngmt.yaml**: Defines a service and Specifies the deployment configuration for the application. It specifies the type of service (e.g., LoadBalancer) and the ports to be exposed. It defines the desired state for the application, including the Docker image `jgurbani/payrollmngmt:0.0.1`, the number of replicas, and the container ports.

- **k8s/loadgen.yaml**: Deploys a small container that generates load to the application and also access endpoints to simulate errors (/error/runtime) or delayed response( /error/delay )
  

## Deployment Instructions

1. **Set up your Kubernetes cluster**: Ensure you have access to a running Kubernetes cluster.

2. **Create the namespace**: Apply the namespace configuration:
   ```
   kubectl apply -f k8s/namespace.yaml
   ```

3. **Deploy the application**: Apply the deployment configuration:
   ```
   kubectl apply -f k8s/payrollmngmt.yaml
   ```


   ```

## Accessing the Application

After deployment, you can access the application using the service type defined in `service.yaml`. If you have configured ingress, you can access it via the specified hostname or IP.

## Notes

- Ensure that the Docker image `jgurbani/payrollmngmt:0.0.1` is available in your container registry.
- Modify the configuration files as needed to suit your environment and requirements.