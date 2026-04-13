# Project Name: EliteMo

#### Team Members: Anisha Penikalapati, Chaithra Surinani, Julia Pajaro, Kritika Rajpal, Mary Penta Reddy
___
## **<ins>Project Information</ins>**

The project is an e-commerce clothing platform that allows users to browse products,
view details, add products to cart and complete purchases. A key feature of the site
is the personalized recommendation system that uses the results of a user's personality
assessment to recommend clothing. The website will also highlight trending items and display 
suggestions of frequently bought together items to help users build an outfit and discover new styles.
Overall, the purpose is to provide an engaging platform that helps users find clothing
that fits their personality.
___

## **<ins>How to Set Up the Application</ins>**

#### _Clone the Repository Using Repository Link_
#### a) In the GitHub repository, click the green **Code** button and copy the HTTPS link.
#### b) Open the terminal and navigate to the folder where you want the project to be saved.
#### c) Run the clone command: `git clone "HTTPS link"`
#### d) After cloning, open the project folder in your IDE (IntelliJ is preferred)

___
## How to Run the Application
### **<ins>Option A: Run the Graphical Interface</ins>**

#### Step 1: Open the Program
Run `Main.java`.
This opens a window which you should expand to see the full website.

<img src="resources/ReadMe/Screenshot%202026-03-10%20212327.png" width="100%">

### **<ins>Option B: How to Run Using the Apache Ant Build Script</ins>**


### _Requirements_
#### a) Apache Ant installed and added to your system PATH
#### b) Java JDK 17+ installed

### _Build the Project_
#### a) First enter `ant compile` into the command terminal - Compiles all Java source files and resources (CSVs, images, text files) into the build/ directory
Then you can either package the application using 
#### c) `ant jar` - Packages the application into a runnable JAR located in dist/.
To run the jar: enter `java -jar dist/myproject.jar` into the terminal or go into the dist folder and double-click the jar

Or you can run it using:
#### b) `ant run` - Runs the program using the compiled classes in the build/ directory.
#### d) `ant clean` - Deletes the build/ and dist/ directories to restart.

___

## How to Use the Application
- Shopping Page
  - Browse all clothing items
  - Filter by size, color, material, rating, and category
  Product Detail Page
  - View product image
  - Read product description
  - Check price and rating
  - Add item to cart
- Cart
  - View selected items
  - See count of items in cart
  - Proceed to checkout
- Checkout Page
  - Review your order
  - See total cost of purchase
  - Confirm purchase
  - View “Frequently Bought Together” suggestions
- Personality Quiz
  - Answer a short quiz 
  - Receive personalized clothing recommendations
- Friends
  - View what your friends have bought
 

> TIP: To see the product details click the product name
        



