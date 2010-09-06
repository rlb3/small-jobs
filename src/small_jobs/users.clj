(ns small-jobs.users
  (:require [small-jobs.db :as db]))

(defn create-user
  "Create a user and insert into the database"
  [username email skills friends groups temp-groups]
  (db/insert-user {:username username :email email :skills skills :friends friends :groups groups :temp-groups temp-groups}))

(defn find-user-by-username
  "User search function"
  [username]
  (db/find-user-by-username username))

(defn find-user-by-email
  "User search function"
  [email]
  (db/find-user-by-email email))

(defn find-user-by-id
  "User search function"
  [id]
  (db/find-user-by-id id))

(defn update-user-friends
  "Add user ids to their friends list"
  [user friends]
  (db/update-user-friends user friends))

(defn get-friends
  "Find all a users friends"
  [user]
  (map find-user-by-id (:friends user)))
