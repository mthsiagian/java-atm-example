##ATM Simulation - STAGE 2

This Repository contains ATM simulation application written in java 8. 

##Features:
1. Withdrawal
2. Fund Transfer
3. Transaction History

## How to Run:
```sh
java -jar application.jar
```

### Notes :
- This application will initiate account from csv file.
- Provide path to csv file as environment variable.
  ```set ENV_ACC_PATH=${path_to_file} ```
  ``` ENV_ACC_PATH=path to file java -jar yourjar.jar ```
- If no path provided, by default uses fixture from `/${WORKDIR}/data/account.csv`
- Non-existing file will throw error.