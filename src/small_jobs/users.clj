(ns small-jobs.users
  (:require [small-jobs.db :as db]))

(defn create-user [username email skills friends groups temp-groups]
  (db/insert-user {:username username :email email :skills skills :friends friends :groups groups :temp-groups temp-groups}))

(defn find-user-by-username [username]
  (db/find-user-by-username username))

(defn find-user-by-email [email]
  (db/find-user-by-email email))

(defn update-user-friends [user friends]
  (db/update-user-friends user friends))
