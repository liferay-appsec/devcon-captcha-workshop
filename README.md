# Liferay CAPTCHA Client Extensions Workspace

This repository is a Liferay workspace containing a set of client extensions to integrate various CAPTCHA solutions into Liferay.

The workspace includes four different CAPTCHA implementations, each with a frontend (custom element) and a backend (Spring Boot service):

1.  **hCaptcha:**

    * `liferay-hcaptcha-custom-element`: A React-based custom element that renders the hCaptcha widget.
    * `liferay-hcaptcha-etc-spring-boot`: A Spring Boot microservice that handles backend validation against the hCaptcha API.

2.  **Google reCAPTCHA:**

    * `liferay-recaptcha-custom-element`: A React-based custom element that renders the Google reCAPTCHA widget.
    * `liferay-recaptcha-etc-spring-boot`: A Spring Boot microservice for backend validation against the Google reCAPTCHA API.

3.  **Really Simple CAPTCHA:**

    * `liferay-devcon-really-simple-captcha-custom-element`: A simple React-based "I'm not a robot" checkbox.
    * `liferay-devcon-really-simple-captcha-etc-spring-boot`: A Spring Boot microservice that validates if the checkbox was checked.

4.  **ALTCHA:**

    * `liferay-altcha-custom-element`: A custom element that renders the ALTCHA widget.
    * `liferay-altcha-etc-spring-boot`: A Spring Boot microservice for backend validation of the ALTCHA solution.
    * **External Challenge Server:** This solution also requires running the separate `altcha-starter-java` project to self-host the challenge generation.

## Prerequisites

Before you begin, ensure you have the following installed:

* [Liferay Blade CLI](https://www.google.com/search?q=https://liferay.dev/tooling/blade-cli)
* Java (JDK)
* Node.js
* Maven (for the ALTCHA challenge server)

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

**Before running, you must add your API keys where required.**

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

-----

#### Option 4: ALTCHA

The ALTCHA solution requires **two** separate backend services to be running:

1.  The external **Challenge Server** (from the `altcha-starter-java` project).
2.  The Liferay **Validation Service** (from this workspace).

**1. Run the External Challenge Server**

This server generates the ALTCHA challenge. You will need to run the `altcha-starter-java` project locally, see https://github.com/altcha-org/altcha-starter-java.

* In a new terminal, navigate to the `altcha-starter-java` directory (e.g., `altcha-org/altcha-starter-java/altcha-starter-java-e73a78433178894ad1c088178c28ec442664a8d7`).
* Set the secret HMAC key as an environment variable. This key *must* be shared with the Liferay validation service.
  ```bash
  export ALTCHA_HMAC_KEY="your-secret-hmac-key-here"
  ```
* Run the server using Maven:
  ```bash
  mvn spring-boot:run
  ```
* This server will run on `localhost:3000`. The Liferay frontend widget is hardcoded to find it at this address.

**2. Configure and Run the Liferay Validation Service**

This server validates the solution provided by the frontend.

* **Configure Secret Key:** Open `client-extensions/liferay-altcha-etc-spring-boot/src/main/resources/application-default.properties`. Add the `liferay.altcha.secret` property. **Its value must be identical to the `ALTCHA_HMAC_KEY`** you set for the challenge server.

  ```properties
  # ...
  server.port=58084

  # Must match the ALTCHA_HMAC_KEY from the challenge server
  liferay.altcha.secret=your-secret-hmac-key-here
  ```

* **Run the Spring Boot App:**
  In *another* new terminal, navigate to the Liferay service directory:

  ```bash
  cd client-extensions/liferay-altcha-etc-spring-boot
  ./gradlew bootRun
  ```

  This service will run on `localhost:58084`.
