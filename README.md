<h1 align="center" style="font-weight: bold;">Email Microservice - Serverless ☁️</h1>

<div align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="java">
  <img src="https://img.shields.io/badge/AWS%20Lambda-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white" alt="AWS Lambda">
  <img src="https://img.shields.io/badge/AWS%20SQS-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white" alt="AWS SQS">
  <img src="https://img.shields.io/badge/AWS%20SES-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white" alt="AWS SES">
</div>

<br>
<p align="center">
 <a href="#started">Getting Started</a> •
 <a href="#features">Features</a> •
 <a href="#libraries">External Libraries</a>
</p>

<br>

<p align="center">
  <b>Serverless microservice for sending emails via AWS SES, triggered by AWS SQS and executed in AWS Lambda.</b>
</p>

---

<h2 id="started">🚀 Getting Started</h2>

### Prerequisites

Make sure you have:

- Java 17 or later
- Maven for dependency management
- An AWS account configured for **Lambda, SQS, and SES**

### Cloning the Project

Clone the repository:

```bash
git clone https://github.com/RafaYudi33/EmailMicroservice.git
cd EmailMicroservice
```

### Deploying to AWS Lambda

Since this is a **serverless** version, no `application.properties` file is required. The AWS Lambda function reads configurations from environment variables.

Deploy the function using:
```bash
mvn clean package
aws lambda update-function-code --function-name EmailMicroserviceLambda --zip-file fileb://target/email-microservice-lambda.jar
```

---

<h2 id="features">📍 Features</h2>

> ### 📥 **1. AWS SQS Trigger**
> - AWS Lambda automatically triggers when a new message arrives in the **SQS** queue.

---

> ### ✉️ **2. Email Sending via AWS SES**
> - Processes messages from the queue and sends emails using **AWS SES**.
> - Supports dynamic email templates.

---

> ### 🔄 **3. Integration with ResidencialSync**
> - Messages in the **SQS** queue are sent by the **ResidencialSync** application.
> - [🔗 **Click here to access the ResidencialSync project**](https://github.com/RafaYudi33/ResidencialSystem)

---

> ### 🌍 **4. RESTful Version Available**
> - A **RESTful** version of this microservice exists, implemented with a traditional API.
> - To access the REST version, switch to the branch:
>   ```bash
>   git checkout microservice-rest
>   ```

---

<h2 id="libraries">🔌 External Libraries</h2>

To enhance the application's functionality, the following external libraries were used:

- **AWS SDK for Java**: Integration with **AWS Lambda, SQS, and SES**.

---

📌 *This microservice was developed to provide an efficient and scalable email sending solution using AWS Lambda.* 🚀

