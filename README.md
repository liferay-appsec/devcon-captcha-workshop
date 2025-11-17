# Liferay CAPTCHA Client Extensions Workspace

This repository is a Liferay workspace containing a set of client extensions to integrate various CAPTCHA solutions into Liferay.

The workspace includes three different CAPTCHA implementations, each with a frontend (custom element) and a backend (Spring Boot service):

1.  **hCaptcha:**

      * `liferay-hcaptcha-custom-element`: A React-based custom element that renders the hCaptcha widget.
      * `liferay-hcaptcha-etc-spring-boot`: A Spring Boot microservice that handles backend validation against the hCaptcha API.

2.  **Google reCAPTCHA:**

      * `liferay-recaptcha-custom-element`: A React-based custom element that renders the Google reCAPTCHA widget.
      * `liferay-recaptcha-etc-spring-boot`: A Spring Boot microservice for backend validation against the Google reCAPTCHA API.

3.  **Really Simple CAPTCHA:**

      * `liferay-devcon-really-simple-captcha-custom-element`: A simple React-based "I'm not a robot" checkbox.
      * `liferay-devcon-really-simple-captcha-etc-spring-boot`: A Spring Boot microservice that validates if the checkbox was checked.

## Prerequisites

Before you begin, ensure you have the following installed:

  * [Liferay Blade CLI](https://www.google.com/search?q=https://liferay.dev/tooling/blade-cli)
  * Java (JDK)
  * Node.js

-----

## 1\. Initialize and Run Liferay

First, you need to download and start a Liferay bundle associated with this workspace.

1.  **Initialize the Liferay Bundle:**
    Run the following command from the root of the workspace to download the Liferay server:

    ```bash
    blade gw initBundle
    ```

2.  **Run the Liferay Server:**
    Once the download is complete, start the Liferay server:

    ```bash
    blade server run
    ```

-----

## 2\. Deploy Client Extensions

Deploying requires two steps: deploying the YAML descriptors to Liferay and running the external Spring Boot applications.

### A. Deploy Descriptors to Liferay

From the workspace root, run the `deploy` command. This will find all `client-extension.yaml` files (for both frontend and backend) and deploy them to your running Liferay instance.

```bash
blade gw deploy
```

### B. Configure and Run Backend Services

You must run the Spring Boot application that corresponds to the CAPTCHA you want to use.

**Before running, you must add your API keys.**

-----

#### Option 1: hCaptcha

1.  **Configure Keys:**

      * **Site Key (Frontend):** Open `client-extensions/liferay-hcaptcha-custom-element/src/components/Captcha.js` and replace `"your-site-key"` with your hCaptcha Site Key.
      * **Secret Key (Backend):** Open `client-extensions/liferay-hcaptcha-etc-spring-boot/src/main/resources/application-default.properties` and replace `myfancypassword` with your hCaptcha Secret Key.

2.  **Run the Spring Boot App:**
    In a new terminal, navigate to the service directory and use Gradle to run the application.

    ```bash
    cd client-extensions/liferay-hcaptcha-etc-spring-boot
    ./gradlew bootRun
    ```

    This service will run on `localhost:58082`.

-----

#### Option 2: Google reCAPTCHA

1.  **Configure Keys:**

      * **Site Key (Frontend):** Open `client-extensions/liferay-recaptcha-custom-element/src/components/Captcha.js` and replace the placeholder `sitekey` with your reCAPTCHA Site Key.
      * **Secret Key (Backend):** Open `client-extensions/liferay-recaptcha-etc-spring-boot/src/main/resources/application-default.properties` and replace the placeholder `secret` with your reCAPTCHA Secret Key.

2.  **Run the Spring Boot App:**
    In a new terminal, navigate to the service directory and run the application.

    ```bash
    cd client-extensions/liferay-recaptcha-etc-spring-boot
    ./gradlew bootRun
    ```

    This service will run on `localhost:58081`.

-----

#### Option 3: Really Simple CAPTCHA

1.  **Configure Keys:**
    No API keys are required for this example.

2.  **Run the Spring Boot App:**
    In a new terminal, navigate to the service directory and run the application.

    ```bash
    cd client-extensions/liferay-devcon-really-simple-captcha-etc-spring-boot
    ./gradlew bootRun
    ```

    This service will run on `localhost:58083`.