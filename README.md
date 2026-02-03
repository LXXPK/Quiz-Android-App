# 📱 SmartQuiz – Android Quiz Application

SmartQuiz is an Android quiz application developed using **Jetpack Compose** and **MVVM architecture**.  
The project is built as a **team-based application (4 members)** with a strong emphasis on **clean code, modular design, and scalability**.

---

## 📌 Project Description

SmartQuiz allows users to:
- Sign in using Google authentication
- View available quizzes
- Attempt quizzes with multiple questions
- Select interests to personalize quiz suggestions
- View quiz attempt history
- View quiz results

The application is designed to support multiple features developed independently and integrated seamlessly.

---

## 🧩 Features

### 🔐 Authentication
- Google Sign-In
- User session handling

### 🏠 Home
- Display active quizzes
- Display suggested quizzes based on interests

### 🧠 Quiz
- Quiz list screen
- Quiz play screen (questions & options)
- Quiz result screen

### 👤 Profile
- View user details
- Select and edit quiz interests

### 📜 History
- View previously attempted quizzes
- Display scores and timestamps

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM (Model–View–ViewModel)
- **Navigation:** Navigation Compose
- **Database:** Room (SQLite)
- **Concurrency:** Kotlin Coroutines
- **Build System:** Gradle (Kotlin DSL)

---

## 🏗️ Architecture Overview

The application follows a **feature-based MVVM architecture**:

UI (Compose Screens)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;↓  
&nbsp;ViewModel  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;↓  
&nbsp;Repository  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;↓  
Room Database (DAO → Entity)



### Architectural Guidelines
- No business logic in UI
- ViewModels manage state and logic
- Repositories abstract data operations
- Database access only via DAO
- Navigation logic separated from UI

---

## 📂 Project Structure

com.example.smartquiz  
│  
├── data  
│ ├── local  
│ │ ├── entity  
│ │ ├── dao  
│ │ └── database  
│ └── repository  
│  
├── navigation  
│ ├── Routes.kt  
│ ├── RootNavGraph.kt  
│ ├── auth  
│ ├── home  
│ ├── quiz  
│ └── profile  
│
├── ui  
│ ├── auth  
│ ├── home  
│ ├── quiz  
│ ├── profile  
│ └── history  
│
├── viewmodel  
├── utils  
├── theme  
├── MainActivity.kt    
└── QuizApplication.kt 


---

## 🧭 Navigation Design

- Centralized navigation contract using `Routes.kt`
- Feature-wise navigation graphs
- Single root NavHost in `MainActivity`
- Screens communicate via callbacks
- Navigation logic is not placed inside ViewModels

---

## Diagrams

### Data Flow Diagram (Level 1)

This DFD illustrates the high-level flow of data between the user, the main processes of the app, and the data stores.

```mermaid
graph TD
    subgraph "User Interface"
        UI_Login["1.0<br>Authentication UI"]
        UI_Quiz["2.0<br>Quiz UI"]
        UI_Results["3.0<br>Results UI"]
    end

    subgraph "Processes"
        P_Auth["1.1<br>Manage Authentication"]
        P_Quiz["2.1<br>Manage Quiz"]
        P_Score["2.2<br>Calculate & Store Score"]
    end

    subgraph "Data Stores"
        DS_Users[("D1<br>Users/Scores")]
        DS_Questions[("D2<br>Questions")]
    end
    
    E_User([User])

    E_User -- User Credentials --> UI_Login
    UI_Login -- Auth Request --> P_Auth
    P_Auth -- User Record --> DS_Users
    P_Auth -- Auth Status --> UI_Login
    UI_Login -- Auth Token --> UI_Quiz

    E_User -- Start Quiz --> UI_Quiz
    UI_Quiz -- Fetch Questions Request --> P_Quiz
    P_Quiz -- Fetches --> DS_Questions
    DS_Questions -- Questions --> P_Quiz
    P_Quiz -- Questions --> UI_Quiz

    E_User -- Answers --> UI_Quiz
    UI_Quiz -- Submitted Answers --> P_Score
    P_Score -- Final Score --> UI_Results
    P_Score -- Updates --> DS_Users
    UI_Results -- Display Score --> E_User
```

### Flowchart: Taking a Quiz

This flowchart details the step-by-step process a user goes through when taking a quiz.

```mermaid
flowchart TD
    A[Start: User Clicks 'Start Quiz' on Home Screen] --> B{Navigate to Quiz Screen}
    B --> C[ViewModel loads questions from database]
    C --> D{Display first question and options}
    D --> E[User selects an answer]
    E --> F{Is it the correct answer?}
    F -- Yes --> G[Highlight answer as correct]
    F -- No --> H[Highlight correct and incorrect answers]
    G & H --> I{Is it the last question?}
    I -- No --> J[Load and display next question]
    J --> E
    I -- Yes --> K[Calculate final score]
    K --> L{Navigate to Results Screen}
    L --> M[Display final score, summary, and correct/incorrect count]
    M --> N[End]
```

### System Architecture Diagram

This diagram visualizes the MVVM architecture and data flow described in the repository:
```mermaid
flowchart TD
    subgraph A [Presentation Layer]
        UI[UI Jetpack Compose Screens]
        VM[ViewModels]
    end

    subgraph B [Domain Layer]
        Repo[Repositories]
    end

    subgraph C [Data Layer]
        DAO[Room DAOs]
        DB[(Room Database)]
    end

    User[App User] -->|Interacts with| UI
    UI -->|Observes State/Events| VM
    VM -->|Calls| Repo
    Repo -->|Uses| DAO
    DAO -->|Reads/Writes| DB

    Ext[Google Auth API] -->|Auth Data| Repo
    VM -->|Updates State| UI
```

----
