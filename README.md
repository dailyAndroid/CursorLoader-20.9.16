# CursorLoader-20.9.16

"If the application's targetSdkVersion is set to less than 23. It will be assumed that application is not tested with 
new permission system yet and will switch to the same old behavior: user has to accept every single permission at 
install time and they will be all granted once installed !" 

Since the new update, permission to access phonebook must be checked during runtime,to avoid security errors,
change the targeted SDK version to 22 in the build.gradle file. :)
