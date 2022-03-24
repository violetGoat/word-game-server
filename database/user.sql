-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER wordlist_owner
    WITH PASSWORD 'kiteStrongBarberLength';

GRANT ALL
    ON ALL TABLES IN SCHEMA public
    TO wordlist_owner;

CREATE USER wordlist_appuser
    WITH PASSWORD 'kiteStrongBarberLength';

GRANT SELECT, INSERT, UPDATE, DELETE
    ON ALL TABLES IN SCHEMA public
    TO wordlist_appuser;
