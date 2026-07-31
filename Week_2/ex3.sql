-- ============================================================================
-- Exercise 3: Stored Procedures
-- ============================================================================
SET SERVEROUTPUT ON;

-- ----------------------------------------------------------------------------
-- Scenario 1: ProcessMonthlyInterest - add 1% interest to all savings accounts.
-- ----------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest IS
BEGIN
    UPDATE Accounts
    SET    Balance = Balance + (Balance * 0.01)
    WHERE  AccountType = 'Savings';

    COMMIT;
    DBMS_OUTPUT.PUT_LINE(SQL%ROWCOUNT || ' savings account(s) credited with interest.');
END;
/

-- ----------------------------------------------------------------------------
-- Scenario 2: UpdateEmployeeBonus - add bonus % to salaries of a department.
-- ----------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_dept      IN VARCHAR2,
    p_bonus_pct IN NUMBER
) IS
BEGIN
    UPDATE Employees
    SET    Salary = Salary + (Salary * p_bonus_pct / 100)
    WHERE  Department = p_dept;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE(SQL%ROWCOUNT || ' employee(s) in ' || p_dept
        || ' updated with ' || p_bonus_pct || '% bonus.');
END;
/

-- ----------------------------------------------------------------------------
-- Scenario 3: TransferFunds - move amount between accounts if funds suffice.
-- ----------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE TransferFunds (
    p_from_acc IN NUMBER,
    p_to_acc   IN NUMBER,
    p_amount   IN NUMBER
) IS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance
    FROM   Accounts
    WHERE  AccountID = p_from_acc
    FOR UPDATE;

    IF v_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20002, 'Insufficient balance in account ' || p_from_acc);
    END IF;

    UPDATE Accounts SET Balance = Balance - p_amount WHERE AccountID = p_from_acc;
    UPDATE Accounts SET Balance = Balance + p_amount WHERE AccountID = p_to_acc;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transferred ' || p_amount || ' from account '
        || p_from_acc || ' to account ' || p_to_acc || '.');
END;
/

-- ---- Demonstration ---------------------------------------------------------
BEGIN
    ProcessMonthlyInterest;
    UpdateEmployeeBonus('IT', 10);
    TransferFunds(2, 1, 100);
END;
/