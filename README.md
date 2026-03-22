## Project Name: EliteMo

#### Team Members: Anisha Penikalapati, Chaithra Surinani, Julia Pajaro, Kritika Rajpal, Mary Penta Reddy

## Project Information

The project is an e-commerce clothing platform that allows users to browse products,
view details, add products to cart and complete purchases. A key feature of the site
is the personalized recommendation system that uses the results of a user's personality
assessment to recommend clothing. The website will also highlight trending items and display 
suggestions of frequently bought together items to help users build an outfit and discover new styles.
Overall, the purpose is to provide an engaging platform that helps users find clothing
that fits their personality.

## How to Run

### 1: Clone the Repository Using Repository Link

#### a) In the GitHub repository, click the green **Code** button and copy the HTTPS link.
#### b) Open the terminal and navigate to the folder where you want the project to be saved.
#### c) Run the clone command: `git clone "HTTPS link"`
#### d) After cloning, open the project folder in your IDE (IntelliJ is preferred)

### 2: Run the Graphical Interface
#### Step 1: Open the Program
Run `Main.java`.
This opens a window which you should expand to see the full website.

<img src="resources/ReadMe/Screenshot%202026-03-10%20212327.png" width="100%">


## How to Run Using Build Script
### 1. Requirements
#### a) Apache Ant installed and added to your system PATH
#### b) Java JDK 17+ installed

### 2. Build the Project
#### a) `ant compile` - Compiles all Java source files and resources (CSVs, images, text files) into the build/ directory
#### b) `ant run` - Runs the program using the compiled classes in the build/ directory.
#### c) `ant jar` - Packages the application into a runnable JAR located in dist/.
To run the jar: `java -jar dist/myproject.jar` or go into the dist folder and double-click the jar
#### d) `ant clean` - Deletes the build/ and dist/ directories to restart.

> Note: To run the project simply use this command `ant run`




        



