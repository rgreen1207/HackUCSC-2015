   public void loginPost(View view){
	//need to get the username from the form and convert to a string
	//need to get the password from the form and convert to a string
	//create a new SignInActivity and pass in the context, and the status message screen
   
	String username; 
	String password;
	
	new SignInActivity(this, 
						//whatever the status is called,
						).execute(username, password));
						
   
      //String username = usernameField.getText().toString();
      //String password = passwordField.getText().toString();
      //don't need this
	  //method.setText("Post Method");
      //new SigninActivity(this,status,role,1).execute(username,password);

   }