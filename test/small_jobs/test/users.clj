(ns small-jobs.test.users
  (:use [small-jobs.users] :reload)
  (:use [clojure.test])
  (:use [somnium.congomongo])
  (:require [small-jobs.db :as db]))



(defmacro with-db [& body]
  `(do
     (db/connect "testsj")
     ;; [username email skills friends groups temp-groups]
     (create-user "rlb3" "robert@example.com" [:trombone :programming] [] [] [])
     (create-user "jt" "jt@example.com" [:management :lifecoach] [] [] [])
     ~@body
     (drop-database! "testsj")))


(deftest test-find-user-by-username
  (with-db
    (let [r (find-user-by-username "rlb3")
          j (find-user-by-username "jt")]
      (is (= (:username r) "rlb3"))
      (is (= (:username j) "jt")))))

(deftest test-find-user-by-email
  (with-db
    (let [r (find-user-by-email "robert@example.com")
          j (find-user-by-email "jt@example.com")]
      (is (= (:username r) "rlb3"))
      (is (= (:username j) "jt")))))


(run-tests 'small-jobs.test.users)

