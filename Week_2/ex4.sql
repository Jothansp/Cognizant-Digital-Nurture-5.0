-- ============================================================================
-- Exercise 4: Functions
-- ============================================================================
SET SERVEROUTPUT ON;

-- ----------------------------------------------------------------------------
-- Scenario 1: CalculateAge - return age in completed years from DOB.
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION CalculateAge (p_dob IN DATE)
RETURN NUMBER IS
BEGIN
    RETURN FLOOR(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
END;
/

-- ----------------------------------------------------------------------------
-- Scenario 2: CalculateMonthlyInstallment - EMI from amount, annual rate %, years.
--   EMI = P * r * (1+r)^n / ((1+r)^n - 1) ; r = monthly rate, n = months.
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment (
    p_amount IN NUMBER,   -- principal
    p_rate   IN NUMBER,   -- annual interest rate in %
    p_years  IN NUMBER    -- loan duration in years
) RETURN NUMBER IS
    v_r   NUMBER;
    v_n   NUMBER;
    v_emi NUMBER;
BEGIN
    v_n := p_years * 12;
    v_r := p_rate / 12 / 100;

    IF v_r = 0 THEN
        v_emi := p_amount / v_n;
    ELSE
        v_emi := p_amount * v_r * POWER(1 + v_r, v_n)
                 / (POWER(1 + v_r, v_n) - 1);
    END IF;

    RETURN ROUND(v_emi, 2);
END;
/

-- ----------------------------------------------------------------------------
-- Scenario 3: HasSufficientBalance - BOOLEAN check for an account.
--   (BOOLEAN functions are usable in PL/SQL, not in plain SQL.)
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION HasSufficientBalance (
    p_acc_id IN NUMBER,
    p_amount IN NUMBER
) RETURN BOOLEAN IS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance
    FROM   Accounts
    WHERE  AccountID = p_acc_id;

    RETURN v_balance >= p_amount;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN FALSE;
END;
/

-- ---- Demonstration ---------------------------------------------------------
DECLARE
    v_dob DATE;
BEGIN
    SELECT DOB INTO v_dob FROM Customers WHERE CustomerID = 1;
    DBMS_OUTPUT.PUT_LINE('Age of customer 1  : ' || CalculateAge(v_dob));
    DBMS_OUTPUT.PUT_LINE('EMI (500000@9%,5y) : ' || CalculateMonthlyInstallment(500000, 9, 5));

    IF HasSufficientBalance(1, 500) THEN
        DBMS_OUTPUT.PUT_LINE('Account 1 has sufficient balance for 500.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Account 1 has insufficient balance for 500.');
    END IF;
END;
/