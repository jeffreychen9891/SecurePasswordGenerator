# Secure Password Generator  
Android Application for Generating Random Secure Passwords  
(Individual Git Exercise Project)

---

##  Project Overview  
This Android application generates secure random passwords based on user-selected length.  
The project is created for the Individual Git Exercise assignment at Kwantlen Polytechnic University (KPU) to demonstrate proper usage of Git, including branching, merging, pull requests, commits, and documentation.

The app provides a simple UI where users can:
- Enter the desired password length  
- Generate a random password  
- View the generated password instantly  

---

##  Features

###  Feature 1: Random Password Generation
- Generates a secure random password using:
  - Uppercase letters  
  - Lowercase letters  
  - Numbers  
  - Special characters  
- Users can specify the password length.

###  Feature 2: Password Strength Check (optional)
- Evaluates password strength based on character variety.
- Displays the strength level (Weak / Medium / Strong).

---

## Project Structure

SecurePasswordGenerator/
├── app/
│ ├── src/main/java/... → Main Android source code
│ ├── src/test/java/... → Unit tests (JUnit)
├── .gitignore
├── build.gradle.kts
├── settings.gradle.kts
└── README.md

---

##  Git Branch Workflow  
This repository uses multiple branches as required in the Git exercise:

- `main`  
  Main stable branch for the final merged code.

- `feature/password-generator`  
  Branch implementing the password generation logic.

- `feature/password-strength` *(if implemented)*  
  Branch implementing password strength evaluation.

All feature branches are merged into `main` through **Pull Requests**, and the GitHub Network Graph shows the complete branching and merging history.

---

## How to Run the App

1. Open the project in **Android Studio**  
2. Set up an emulator or connect an Android device  
3. Click **Run ▶**  
4. Enter the desired password length  
5. Press **Generate** to display the new password  

---

##  Running Unit Tests

1. Go to:  
   `app/src/test/java/...`
2. Right-click the test class  
3. Select **Run Tests**  
4. Confirm that:
   - The generated password matches the requested length  
   - The password strength evaluation behaves as expected  

---

##  Video Demonstration Requirements  
This application is designed to satisfy the requirements of the Git Exercise demo video.  
Your final video must show:

###  1. GitHub repository + Network Graph  
Show:  
- Your GitHub account  
- This repository  
- **Insights → Network graph**

###  2. Running the Android application  
- Explain what the program does  
- Enter input → generate password  
- Run your unit tests  
- Explain expected vs actual results  

###  3. Local change → commit → push  
- Make a small local code change (e.g., add a comment)  
- Run  
  ```bash
  git add .
  git commit -m "Small update for demo"
  git push origin
Author

Zhengyu Chen (Jeffrey)
Kwantlen Polytechnic University
INFO 4190 — Individual Git Exercise
