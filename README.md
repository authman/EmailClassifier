EmailClassifier
===============

The Email Classifier uses machine learning to predict whether an email is worth reading. There are two components to the software: Java code to fetch the emails and create the datasets, and Octave scripts that actually run the machine learning algorithms. It was created to deal with email overload. 

This software was created with the idea that all emails can be split into four categories: important and urgent, important and not urgent, unimportant and urgent, and not important and not urgent. 

The Java code only works with gmail accounts. All emails must have a subject line with a digit 1-10, where 1 is the least important/urgent email and 10 is the most important/urgent email. It will create two matrices, X and Y. X is the feature matrix and will mark the presence or absence of a feature with a 1 or 0. Y is the result matrix which will mark whether an email was important/urgent or unimportant/not urgent with a 1 or 0. I used 1000 keywords and sender emails as the feature set. The Java code also requires the JavaMail API. I have included the JavaMail jar file. 

The Octave code uses the X and Y matrices to classify the emails. Although the scripts currently run logistic regression, any classification algorithm could be used. It might be worth exploring how effective a naive bayes classifier works for this use case, since its fairly effective with classifying spam email. The scripts were adapted from Andrew Ng's Machine Learning class. 

Using this on my personal email, I was able to get an accuracy of 75-80 percent. Using diagnostic statistics to assess the program's performance, I got a Mathews Correlation Coefficient of about .4. 

This is an Eclipse project, so I recommend downloading all the files and importing it into eclipse. Alternatively, you can download all the files in the src directory and run them yourself. 
