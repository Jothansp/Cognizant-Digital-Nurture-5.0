-- ============================================================================
-- Exercise 1: Control Structures
-- Run in SQL*Plus / SQL Developer. Enable server output for DBMS_OUTPUT.
-- ============================================================================
SET SERVEROUTPUT ON;

-- One-time schema change required for Scenario 2 (IsVIP flag).
-- Run once; ignore "ORA-01430: column already exists" on re-runs.
ALTER TABLE Customers ADD (IsVIP CHAR(1) DEFAULT 'F');

-- ----------------------------------------------------------------------------
-- Scenario 1: Apply a 1% discount to loan interest rates for customers > 60.
-- (Interpreted as reducing the rate by 1 percentage point. If a 1% relative
--  reduction is intended, use InterestRate * 0.99 instead.)
-- ----------------------------------------------------------------------------
BEGIN
    FOR rec IN (
        SELECT l.LoanID,
               l.InterestRate,
               FLOOR(MONTHS_BETWEEN(SYSDATE, c.DOB) / 12) AS Age
        FROM   Loans l
        JOIN   Customers c ON c.CustomerID = l.CustomerID
    ) LOOP
        IF rec.Age > 60 THEN
            UPDATE Loans
            SET    InterestRate = InterestRate - 1
            WHERE  LoanID = rec.LoanID;

            DBMS_OUTPUT.PUT_LINE('Loan ' || rec.LoanID
                || ': 1% discount applied. New rate = ' || (rec.InterestRate - 1));
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- ----------------------------------------------------------------------------
-- Scenario 2: Set IsVIP = 'T' for customers with balance over 10,000.
-- ----------------------------------------------------------------------------
BEGIN
    FOR rec IN (SELECT CustomerID, Balance FROM Customers) LOOP
        IF rec.Balance > 10000 THEN
            UPDATE Customers SET IsVIP = 'T' WHERE CustomerID = rec.CustomerID;
            DBMS_OUTPUT.PUT_LINE('Customer ' || rec.CustomerID || ' promoted to VIP.');
        ELSE
            UPDATE Customers SET IsVIP = 'F' WHERE CustomerID = rec.CustomerID;
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- ----------------------------------------------------------------------------
-- Scenario 3: Print reminders for loans due within the next 30 days.
-- ----------------------------------------------------------------------------
BEGIN
    FOR rec IN (
        SELECT l.LoanID, c.Name, l.EndDate
        FROM   Loans l
        JOIN   Customers c ON c.CustomerID = l.CustomerID
        WHERE  l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('Reminder: Dear ' || rec.Name
            || ', your loan ' || rec.LoanID || ' is due on '
            || TO_CHAR(rec.EndDate, 'DD-MON-YYYY') || '.');
    END LOOP;
END;
/