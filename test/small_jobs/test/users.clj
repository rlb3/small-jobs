(ns small-jobs.test.users
  (:use [small-jobs.users] :reload)
  (:use [clojure.test])
  (:use [somnium.congomongo])
  (:require [small-jobs.db :as db]))

(db/connect "testsj")

;; [username email skills friends groups temp-groups]
(def rlb3 (create-user "rlb3" "robert@example.com" [:trombone :programming] [] [] []))
(def jt (create-user "jt" "jt@example.com" [:management :lifecoach] [] [] []))

(deftest test-create-user
  (is (= (:username rlb3) "rlb3"))
  (is (= (:username jt) "jt")))

(deftest test-find-user-by-username
  (let [r (find-user-by-username "rlb3")
        j (find-user-by-username "jt")]
    (is (= (:username r) "rlb3"))
    (is (= (:username j) "jt"))))

(deftest test-find-user-by-email
  (let [r (find-user-by-email "robert@example.com")
        j (find-user-by-email "jt@example.com")]
    (is (= (:username r) "rlb3"))
    (is (= (:username j) "jt"))))


(drop-coll! "users")
