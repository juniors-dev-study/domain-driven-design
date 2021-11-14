-- user
INSERT INTO `user` (`id`, `password`, `name`, `info_email_address`, `status`, `created_at`, `updated_at`)
VALUES ('userId1', 'passworrd123', '김개발', 'dev@gm1.com', 'ACTIVATED', NOW(), NOW());

-- auth_code
INSERT INTO auth_code (`user_id`, `code`, created_at, purpose)
VALUES ('userId', 'ABC123', NOW(), 'SIGN_UP');
