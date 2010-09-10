(ns small-jobs.db
  (:require [somnium.congomongo :as db]))

(defn connect
  "Connect to database"
  [db]
  (db/mongo!
   :host "127.0.0.1"
   :db db))

(defn id
  "Retrieve id from user"
  [obj]
  (.toString (:_id obj)))

;; User functions
(defn insert-user
  "Insert user into the database"
  [user]
  (db/insert! :users user))

(defn find-user-by-username
  "Search for user"
  [username]
  (db/fetch-one :users :where {:username username}))

(defn find-user-by-email [email]
  (db/fetch-one :users :where {:email email}))

(defn find-user-by-id
  "Search for user"
  [id-str]
  (filter (fn [user] (= (id user) id-str)) (db/fetch :users)))

(defmacro update-user-collection [user set]
  `(db/update! :users ~user (merge ~user {(keyword '~set) (conj ((keyword '~set) ~user) ~set)})))

(defn update-user-friends
  "Add user ids to their friends list"
  [user friends]
  (update-user-collection user friends))

(defn update-user-skills [user skills]
  (update-user-collection user skills))

(defn update-user-groups [user groups]
  (update-user-collection user groups))

(defn update-user-temp-groups [user temp-groups]
  (update-user-collection user temp-groups))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
