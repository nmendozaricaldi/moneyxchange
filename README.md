# moneyxchange
cambio de divisas

## Build the Project
```
mvn clean install
```

## Projects/Modules
This project contains a number of modules, but here are the main ones you should focus on and run: 
- `moneyxchange-oauth-server` - the Authorization Server (port = 8087)
- `moneyxchange-api` - the Resource Server (port = 8082)

Other Modules: 
- `moneyxchange-angular` - another version of the Password Grant UI Module - using Angular

Finally, you can ignore all other modules. 

## Run the Modules
You can run any sub-module using command line: 
```
mvn spring-boot:run
```

## Run the Angular 5 Modules

- To run Angular5 front-end modules (_moneyxchange-angular_) , we need to build the app first:
```
mvn clean install
```

- Then we need to navigate to our Angular app directory:
```
cd src/main/resources
```

And run the command to download the dependencies:
```
npm install
```

- Finally, we will start our app:
```
npm start
```

