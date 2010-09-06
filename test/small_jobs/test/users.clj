(ns small-jobs.test.users
  (:use [small-jobs.users] :reload)
  (:use [clojure.test])
  (:use [somnium.congomongo])
  (:require [small-jobs.db :as db]))

(def database-name "testsj")

(defn setup! []
  (db/connect database-name)
  ;; [username email skills friends groups temp-groups]
  (create-user "rlb3" "secret" "robert@example.com" [:trombone :programming] [] [] [])
  (create-user "jt" "secret" "jt@example.com" [:management :lifecoach] [] [] []))

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
      (is (= (r :username) "rlb3"))
      (is (= (j :username) "jt")))))

(deftest test-find-user-by-email
  (with-db
    (let [r (find-user-by-email "robert@example.com")
          j (find-user-by-email "jt@example.com")]
      (is (= (r :username) "rlb3"))
      (is (= (j :username) "jt")))))

(deftest test-update-user-friends
  (with-db
    (let [r (find-user-by-username "rlb3")
          j (find-user-by-username "jt")]
      (is (= (count (r :friends)) 0))
      (update-user-friends r (j :id))
      (let [new-r (find-user-by-username "rlb3")]
        (is (= (count (new-r :friends)) 1))
        (is (= ((new-r :friends) 0) (j :id)))))))

(deftest test-get-friend
  (with-db
    (let [r (find-user-by-username "rlb3")
          j (find-user-by-username "jt")]
      (update-user-friends r (db/id j))
      (let [new-r (find-user-by-username "rlb3")
            jt (first (get-friends new-r))]
        (is (= j jt))))))

(run-tests 'small-jobs.test.users)
