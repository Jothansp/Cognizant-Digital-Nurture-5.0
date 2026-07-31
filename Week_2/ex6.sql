-- ============================================================================
-- Exercise 6: Cursors (explicit cursors)
-- ============================================================================
SET SERVEROUTPUT ON;

-- ----------------------------------------------------------------------------
-- Scenario 1: GenerateMonthlyStatements - transactions for the current month.
-- ----------------------------------------------------------------------------
DECLARE
    CURSOR GenerateMonthlyStatements IS
        SELECT a.CustomerID, t.AccountID, t.TransactionID,
               t.TransactionDate, t.Amount, t.TransactionType
        FROM   Transactions t
        JOIN   Accounts a ON a.AccountID = t.AccountID
        WHERE  TRUNC(t.TransactionDate, 'MM') = TRUNC(SYSDATE, 'MM')
        ORDER BY a.CustomerID, t.TransactionDate;
    v_rec GenerateMonthlyStatements%ROWTYPE;
BEGIN
    OPEN GenerateMonthlyStatements;
    LOOP
        FETCH GenerateMonthlyStatements INTO v_rec;
        EXIT WHEN GenerateMonthlyStatements%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('Cust ' || v_rec.CustomerID
            || ' | Acc ' || v_rec.AccountID
            || ' | Txn ' || v_rec.TransactionID
            || ' | ' || v_rec.TransactionType
            || ' | ' || v_rec.Amount
            || ' | ' || TO_CHAR(v_rec.TransactionDate, 'DD-MON-YYYY'));
    END LOOP;
    CLOSE GenerateMonthlyStatements;
END;
/

-- ----------------------------------------------------------------------------
-- Scenario 2: ApplyAnnualFee - deduct a fixed annual fee from every account.
-- ----------------------------------------------------------------------------
DECLARE
    CURSOR ApplyAnnualFee IS
        SELECT AccountID, Balance FROM Accounts FOR UPDATE;
    v_rec ApplyAnnualFee%ROWTYPE;
    v_fee CONSTANT NUMBER := 500;
BEGIN
    OPEN ApplyAnnualFee;
    LOOP
        FETCH ApplyAnnualFee INTO v_rec;
        EXIT WHEN ApplyAnnualFee%NOTFOUND;

        UPDATE Accounts
        SET    Balance = Balance - v_fee
        WHERE  CURRENT OF ApplyAnnualFee;

        DBMS_OUTPUT.PUT_LINE('Annual fee ' || v_fee
            || ' deducted from account ' || v_rec.AccountID);
    END LOOP;
    CLOSE ApplyAnnualFee;
    COMMIT;
END;
/

-- ----------------------------------------------------------------------------
-- Scenario 3: UpdateLoanInterestRates - apply a new rate policy to all loans.
--   Policy assumed: high-value loans (>= 100000) get a 0.5% concession;
--   all other loans get a 1% concession. Adjust thresholds as per actual policy.
-- ----------------------------------------------------------------------------
DECLARE
    CURSOR UpdateLoanInterestRates IS
        SELECT LoanID, LoanAmount, InterestRate FROM Loans FOR UPDATE;
    v_rec      UpdateLoanInterestRates%ROWTYPE;
    v_new_rate NUMBER;
BEGIN
    OPEN UpdateLoanInterestRates;
    LOOP
        FETCH UpdateLoanInterestRates INTO v_rec;
        EXIT WHEN UpdateLoanInterestRates%NOTFOUND;

        IF v_rec.LoanAmount >= 100000 THEN
            v_new_rate := v_rec.InterestRate - 0.5;
        ELSE
            v_new_rate := v_rec.InterestRate - 1;
        END IF;

        UPDATE Loans
        SET    InterestRate = v_new_rate
        WHERE  CURRENT OF UpdateLoanInterestRates;

        DBMS_OUTPUT.PUT_LINE('Loan ' || v_rec.LoanID
            || ': rate revised to ' || v_new_rate);
    END LOOP;
    CLOSE UpdateLoanInterestRates;
    COMMIT;
END;
/