(ns small-jobs.db
  (:require [somnium.congomongo :as db]))

(defn connect [db]
  (db/mongo!
   :host "127.0.0.1"
   :db db))

(defn id [obj]
  (.toString (:_id obj)))

(defn insert-user [user]
  (db/insert! :users
              user))

(defn find-user-by-username [username]
  (db/fetch-one :users :where {:username username}))

(defn find-user-by-email [email]
  (db/fetch-one :users :where {:email email}))

(defn update-user-friends [user friends]
  (db/update! :users user (merge user {:friends (conj (:friends user) friends)})))

(defn update-user-skills [user skills]
  (db/update! :users user (merge user {:skills (conj (:skills user) skills)})))

(defn update-user-groups [user groups]
  (db/update! :users user (merge user {:groups (conj (:groups user) groups)})))

(defn update-user-temp-groups [user temp-groups]
  (db/update! :users user (merge user {:temp-groups (conj (:temp-groups user) temp-groups)})))
