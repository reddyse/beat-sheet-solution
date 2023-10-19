# BeatSheet Solution API endpoint
The BeatSheetService is a component of the Spotter application responsible for managing BeatSheets and their interactions. The service also builds and implements a **Markov model** based on the sample data and uses it to predict the next beat and next act. The MarkovModel class initializes the model, builds transitions from the input data, and provides a method to get the next beat or next act based on the current one. 

This README provides an overview of the service, its functionality, and how to use it.

Swagger UI for the endpoint - http://{HOST}:8080/swagger-ui/index.html#/


## Table of Contents

- [Introduction](#introduction)
- [Dependencies](#dependencies)
- [Installation](#installation)
- [Usage](#usage)
  - [Creating a BeatSheet](#creating-a-beatsheet)
  - [Updating a BeatSheet](#updating-a-beatsheet)
  - [Retrieving a BeatSheet](#retrieving-a-beatsheet)
  - [Deleting a BeatSheet](#deleting-a-beatsheet)
  - [Retrieving All BeatSheets](#retrieving-all-beatsheets)
  - [Adding a Beat to a BeatSheet](#adding-a-beat-to-a-beatsheet)
  - [Updating a Beat in a BeatSheet](#updating-a-beat-in-a-beatsheet)
  - [Deleting a Beat in a BeatSheet](#deleting-a-beat-in-a-beatsheet)
  - [Adding an Act to a Beat](#adding-an-act-to-a-beat)
  - [Updating an Act in a Beat](#updating-an-act-in-a-beat)
  - [Deleting an Act in a Beat](#deleting-an-act-in-a-beat)
  - [Predict the next Beat in a BeatSheet](#predicting-next-beat)
  - [Predict the next Act in a Beat](#predicting-next-beat)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Contributing](#contributing)
- [Contact](#contact)


## Introduction

The `BeatSheetService` is designed to manage BeatSheets, a key element in the Spotter application. It provides a set of operations for creating, updating, retrieving, and deleting BeatSheets. Additionally, it offers functionality to work with Beats and Acts within BeatSheets.

## Dependencies

The `BeatSheetService` relies on the following dependencies:

- **Spring Framework:** The service is built as a Spring service and leverages Spring's annotations for dependency injection and REST endpoints.
- **Spotter Data Repository:** The service uses the Spotter data repository to store and retrieve BeatSheets.

## Installation

To use the `BeatSheetService` component, ensure that your project has the following:

1. A Spring application context properly configured.
2. The Spotter data repository set up.

Once the dependencies are in place, you can include the `BeatSheetService` in your project.


## Usage

### Creating a BeatSheet

To create a new BeatSheet, send an HTTP POST request with a JSON representation of the BeatSheet to the `/create` endpoint. Example:

```http
POST /create
Content-Type: application/json

{
  "title": "My BeatSheet",
  "beats": []
}
```

### Updating a BeatSheet

To update an existing BeatSheet, send an HTTP PUT request with the BeatSheet ID and a JSON representation of the updated BeatSheet to the /update/{id} endpoint. Example:

```http
PUT /update/1
Content-Type: application/json

{
  "title": "Updated BeatSheet",
  "beats": []
}
```


### Retrieving a BeatSheet
To retrieve a BeatSheet by ID, send an HTTP GET request to the /retrieve/{id} endpoint. Example:

```http
GET /retrieve/1
```


### Deleting a BeatSheet
To delete a BeatSheet by ID, send an HTTP DELETE request to the /delete/{id} endpoint. Example:
```http
DELETE /delete/1
```


### Retrieving All BeatSheets
To retrieve all available BeatSheets, send an HTTP GET request to the /retrieveAll endpoint. Example:

```http
GET /retrieveAll
```

### Adding a Beat to a BeatSheet
To add a Beat to a BeatSheet, send an HTTP POST request with the Beat ID and a JSON representation of the Beat to the /addBeat/{id} endpoint. Example:

```http
POST /addBeat/1
Content-Type: application/json

{
  "description": "New Beat",
  "timestamp": 12345
}
```

### Updating a Beat in a BeatSheet
To update a Beat in a BeatSheet, send an HTTP PUT request with the BeatSheet ID, Beat ID, and a JSON representation of the updated Beat to the /updateBeat/{id}/{beatId} endpoint. Example:

```http
PUT /updateBeat/1/2
Content-Type: application/json

{
  "description": "Updated Beat",
  "timestamp": 54321
}
```

### Deleting a Beat in a BeatSheet
To delete a Beat in a BeatSheet, send an HTTP DELETE request with the BeatSheet ID and Beat ID to the /deleteBeat/{id}/{beatId} endpoint. Example:

```http
DELETE /deleteBeat/1/2
```


### Adding an Act to a Beat
To add an Act to a Beat, send an HTTP POST request with the BeatSheet ID, Beat ID, and a JSON representation of the Act to the /addAct/{id}/{beatId} endpoint. Example:

```http
POST /addAct/1/2
Content-Type: application/json

{
  "description": "New Act",
  "timestamp": 67890
}
```

### Updating an Act in a Beat
To update an Act in a Beat, send an HTTP PUT request with the BeatSheet ID, Beat ID, Act ID, and a JSON representation of the updated Act to the /updateAct/{id}/{beatId}/{actId} endpoint. Example:

```http
PUT /updateAct/1/2/3
Content-Type: application/json

{
  "id": 0,
  "description": "string",
  "timestamp": "2023-10-19T22:57:02.027Z",
  "duration": 0,
  "cameraAngle": "string"
}

```


### Deleting an Act in a Beat
To delete an Act in a Beat, send an HTTP DELETE request with the BeatSheet ID, Beat ID, and Act ID to the /deleteAct/{id}/{beatId}/{actId} endpoint. Example:

```http
DELETE /deleteAct/1/2/3
```

### Deleting an Act in a Beat
To delete an Act in a Beat, send an HTTP DELETE request with the BeatSheet ID, Beat ID, and Act ID to the /deleteAct/{id}/{beatId}/{actId} endpoint. Example:

```http
DELETE /deleteAct/1/2/3
```

### Predict a new Act in a Beat
To predict the next Act in a Beat, send an HTTP POST request to the /deleteAct/generateNextAct endpoint with the request body. Example:

```http
POST beatsheet/generateNextAct
{
      "id": 202,
      "description": "UPDATEBEAT",
      "timestamp": "2023-10-19T20:03:32.446+00:00",
      "acts": [
        {
          "id": 150,
          "description": "UPDATEDACT",
          "timestamp": "2023-10-19T20:04:37.436+00:00",
          "duration": 0,
          "cameraAngle": "string"
        },
        {
          "id": 180,
          "description": "string",
          "timestamp": "2023-10-19T20:05:29.372+00:00",
          "duration": 0,
          "cameraAngle": "string"
        }
      ]
    }
```

### Predict a new Act in a Beat
To predict the next Act in a Beat, send an HTTP POST request to the /deleteAct/generateNextAct endpoint with the request body. Example:

```http
POST beatsheet/generateNextAct
{
      "id": 202,
      "description": "UPDATEBEAT",
      "timestamp": "2023-10-19T20:03:32.446+00:00",
      "acts": [
        {
          "id": 150,
          "description": "UPDATEDACT",
          "timestamp": "2023-10-19T20:04:37.436+00:00",
          "duration": 0,
          "cameraAngle": "string"
        },
        {
          "id": 180,
          "description": "string",
          "timestamp": "2023-10-19T20:05:29.372+00:00",
          "duration": 0,
          "cameraAngle": "string"
        }
      ]
    }


### Technologies Used

- Java 17
- Springboot 3.1.1
- MySQL 8.1
- Dependencies
  - Lombok
  - MySQL connector
  - Spring Data JPA
  - Spring Web
  - Junit 4.8.1
- RESTful API design

### Getting Started

To run the BeatSheet microservice locally, follow these steps:

## Prerequisites

1. **Docker:** Ensure that you have Docker installed on your local development environment. If not, you can download it from the official [Docker website](https://www.docker.com/get-started).
2. **Kubernetes:** Make sure you have a Kubernetes cluster set up. You can use a local solution like Minikube.
3. **kubectl:** Install `kubectl`, the Kubernetes command-line tool, to interact with your cluster. You can follow the installation guide in the official [Kubernetes documentation](https://kubernetes.io/docs/tasks/tools/install-kubectl/).
4. **Service Code:** Ensure you have the source code of the **BeatSheetService** service locally.

Now that you have the prerequisites ready, follow these steps to deploy the service:

## Deployment Steps
1. **Build Docker Image:** Navigate to the root directory of the service code and build a Docker image for the service. You can use the following command, replacing `your-image-name` and `your-image-tag` with the desired image name and tag:

   ```bash
   docker build -t your-image-name:your-image-tag .
   ```

2. **Push to Container Registry:** Push the Docker image to a container registry (e.g., Docker Hub, Google Container Registry, or Amazon ECR) to make it accessible to your Kubernetes cluster. Ensure you are authenticated to your container registry:

  ```bash
  docker push your-image-name:your-image-tag
  ```

3. **Create Kubernetes Manifests:** Write Kubernetes YAML manifests (deployment, service, config maps, secrets) to define how your application should be deployed and exposed. These manifests will describe the desired state of your application on the cluster. These scripts are already avialable in the repo.

4. **Apply Kubernetes Manifests:** Use kubectl to apply the Kubernetes manifests to your cluster. This will create the necessary resources (Pods, Services, ConfigMaps, etc.) for your application. Replace [your-manifests] with the paths to your manifest files or directories containing manifest files:

  ```bash
  kubectl apply -f mysql-configMap.yaml
  kubectl apply -f mysql-secretss.yaml
  kubectl apply -f db-deployment.yaml
  kubectl apply -f app-deployment.yaml
  ```

5. **Check Deployment Status:** Verify that your application is successfully deployed by checking the status of the Pods and Services. Use kubectl commands to monitor the deployment and check for any errors:

  ```bash
  kubectl get pods
  kubectl get services
  ```

6. **Access Your Service:** To access your application, determine the IP or DNS of the Kubernetes Service. You can use kubectl get svc [your-service-name] to find the external IP or DNS:

  ```bash
  kubectl get svc [your-service-name]
  ```

7. **Update and Rollback:** If you need to update your application, modify the Kubernetes manifest files with your changes and reapply them using kubectl apply. In case of issues, you can roll back to a previous deployment by applying a previous version of the manifests.

8. **Cleanup:** When you're finished with your application, you can clean up by deleting the Kubernetes resources created by your application:

  ```bash
  kubectl delete -f [your-manifests]
  ```



For detailed information about request/response payloads and examples, refer to the microservice's API documentation.

## Contributing

Contributions are welcome! If you want to contribute to this project, please follow these steps:

1. Fork this repository.
2. Create a new branch: `git checkout -b feature/my-feature`.
3. Make your changes and commit them: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin feature/my-feature`.
5. Submit a pull request.



## Contact

If you have any questions or suggestions, please feel free to reach out to us:
- Email: [praneethsettipalli@gmail.com](mailto:praneethsettipalli@gmail.com)
