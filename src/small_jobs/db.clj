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

(defn update-user-friends
  "Add user ids to their friends list"
  [user friends]
  (db/update! :users
              (user :raw)
              (merge (user :raw) {:friends (conj (:friends (user :raw)) friends)})))

(defn update-user-skills [user skills]
  (db/update! :users user (merge user {:skills (conj (:skills user) skills)})))

(defn update-user-groups [user groups]
  (db/update! :users user (merge user {:groups (conj (:groups user) groups)})))

(defn update-user-temp-groups [user temp-groups]
  (db/update! :users user (merge user {:temp-groups (conj (:temp-groups user) temp-groups)})))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
