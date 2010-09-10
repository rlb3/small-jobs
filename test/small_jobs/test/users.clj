(ns small-jobs.test.users
  (:use [small-jobs.users] :reload)
  (:use [clojure.test])
  (:use [somnium.congomongo])
  (:require [small-jobs.db :as db]))

(def database-name "testsj")

(defn setup! []
  (db/connect database-name)
  ;; [username email skills friends groups temp-groups]
  (create-user "rlb3" "robert@example.com" [:trombone :programming] [] [] [])
  (create-user "jt" "jt@example.com" [:management :lifecoach] [] [] []))

(defn teardown! []
  (drop-database! database-name))

(defmacro with-db [& body]
  `(do
     (setup!)
     ~@body
     (teardown!)))

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

(deftest test-update-user-friends
  (with-db
    (let [r (find-user-by-username "rlb3")
          j (find-user-by-username "jt")]
      (is (= (count (:friends r)) 0))
      (update-user-friends r (db/id j))
      (let [new-r (find-user-by-username "rlb3")]
        (is (= (count (:friends new-r)) 1))
        (is (= ((:friends new-r) 0) (db/id j)))))))

(deftest test-get-friend
  (with-db
    (let [r (find-user-by-username "rlb3")
          j (find-user-by-username "jt")]
      (update-user-friends r (db/id j))
      (let [new-r (find-user-by-username "rlb3")
            jt (first (get-friends new-r))]
        (is (= j jt))))))

;; (run-tests 'small-jobs.test.users)

