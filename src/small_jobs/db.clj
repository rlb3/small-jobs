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
