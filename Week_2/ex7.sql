-- ============================================================================
-- Exercise 7: Packages
-- ============================================================================
SET SERVEROUTPUT ON;

-- ----------------------------------------------------------------------------
-- Scenario 1: CustomerManagement
-- ----------------------------------------------------------------------------
CREATE OR REPLACE PACKAGE CustomerManagement AS
    PROCEDURE AddCustomer(p_id NUMBER, p_name VARCHAR2, p_dob DATE, p_balance NUMBER);
    PROCEDURE UpdateCustomer(p_id NUMBER, p_name VARCHAR2, p_balance NUMBER);
    FUNCTION  GetCustomerBalance(p_id NUMBER) RETURN NUMBER;
END CustomerManagement;
/

CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    PROCEDURE AddCustomer(p_id NUMBER, p_name VARCHAR2, p_dob DATE, p_balance NUMBER) IS
    BEGIN
        INSERT INTO Customers(CustomerID, Name, DOB, Balance, LastModified)
        VALUES (p_id, p_name, p_dob, p_balance, SYSDATE);
        COMMIT;
    END AddCustomer;

    PROCEDURE UpdateCustomer(p_id NUMBER, p_name VARCHAR2, p_balance NUMBER) IS
    BEGIN
        UPDATE Customers
        SET    Name = p_name, Balance = p_balance, LastModified = SYSDATE
        WHERE  CustomerID = p_id;
        COMMIT;
    END UpdateCustomer;

    FUNCTION GetCustomerBalance(p_id NUMBER) RETURN NUMBER IS
        v_balance NUMBER;
    BEGIN
        SELECT Balance INTO v_balance FROM Customers WHERE CustomerID = p_id;
        RETURN v_balance;
    END GetCustomerBalance;

END CustomerManagement;
/

-- ----------------------------------------------------------------------------
-- Scenario 2: EmployeeManagement
-- ----------------------------------------------------------------------------
CREATE OR REPLACE PACKAGE EmployeeManagement AS
    PROCEDURE HireEmployee(p_id NUMBER, p_name VARCHAR2, p_position VARCHAR2,
                           p_salary NUMBER, p_dept VARCHAR2);
    PROCEDURE UpdateEmployee(p_id NUMBER, p_position VARCHAR2,
                             p_salary NUMBER, p_dept VARCHAR2);
    FUNCTION  GetAnnualSalary(p_id NUMBER) RETURN NUMBER;
END EmployeeManagement;
/

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

    PROCEDURE HireEmployee(p_id NUMBER, p_name VARCHAR2, p_position VARCHAR2,
                           p_salary NUMBER, p_dept VARCHAR2) IS
    BEGIN
        INSERT INTO Employees(EmployeeID, Name, Position, Salary, Department, HireDate)
        VALUES (p_id, p_name, p_position, p_salary, p_dept, SYSDATE);
        COMMIT;
    END HireEmployee;

    PROCEDURE UpdateEmployee(p_id NUMBER, p_position VARCHAR2,
                             p_salary NUMBER, p_dept VARCHAR2) IS
    BEGIN
        UPDATE Employees
        SET    Position = p_position, Salary = p_salary, Department = p_dept
        WHERE  EmployeeID = p_id;
        COMMIT;
    END UpdateEmployee;

    FUNCTION GetAnnualSalary(p_id NUMBER) RETURN NUMBER IS
        v_salary NUMBER;
    BEGIN
        SELECT Salary * 12 INTO v_salary FROM Employees WHERE EmployeeID = p_id;
        RETURN v_salary;
    END GetAnnualSalary;

END EmployeeManagement;
/

-- ----------------------------------------------------------------------------
-- Scenario 3: AccountOperations
-- ----------------------------------------------------------------------------
CREATE OR REPLACE PACKAGE AccountOperations AS
    PROCEDURE OpenAccount(p_acc_id NUMBER, p_cust_id NUMBER,
                          p_type VARCHAR2, p_balance NUMBER);
    PROCEDURE CloseAccount(p_acc_id NUMBER);
    FUNCTION  GetTotalBalance(p_cust_id NUMBER) RETURN NUMBER;
END AccountOperations;
/

CREATE OR REPLACE PACKAGE BODY AccountOperations AS

    PROCEDURE OpenAccount(p_acc_id NUMBER, p_cust_id NUMBER,
                          p_type VARCHAR2, p_balance NUMBER) IS
    BEGIN
        INSERT INTO Accounts(AccountID, CustomerID, AccountType, Balance, LastModified)
        VALUES (p_acc_id, p_cust_id, p_type, p_balance, SYSDATE);
        COMMIT;
    END OpenAccount;

    PROCEDURE CloseAccount(p_acc_id NUMBER) IS
    BEGIN
        DELETE FROM Accounts WHERE AccountID = p_acc_id;
        COMMIT;
    END CloseAccount;

    FUNCTION GetTotalBalance(p_cust_id NUMBER) RETURN NUMBER IS
        v_total NUMBER;
    BEGIN
        SELECT NVL(SUM(Balance), 0) INTO v_total
        FROM   Accounts
        WHERE  CustomerID = p_cust_id;
        RETURN v_total;
    END GetTotalBalance;

END AccountOperations;
/

-- ---- Demonstration ---------------------------------------------------------
BEGIN
    CustomerManagement.AddCustomer(101, 'Ravi Kumar', TO_DATE('1988-01-10','YYYY-MM-DD'), 20000);
    DBMS_OUTPUT.PUT_LINE('Balance of 101 : ' || CustomerManagement.GetCustomerBalance(101));

    EmployeeManagement.HireEmployee(101, 'Sana Iqbal', 'Analyst', 55000, 'Finance');
    DBMS_OUTPUT.PUT_LINE('Annual salary  : ' || EmployeeManagement.GetAnnualSalary(101));

    AccountOperations.OpenAccount(201, 101, 'Savings', 5000);
    DBMS_OUTPUT.PUT_LINE('Total balance  : ' || AccountOperations.GetTotalBalance(101));
END;
/   