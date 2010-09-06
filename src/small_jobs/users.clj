(ns small-jobs.users
  (:require [small-jobs.db :as db]))

(defn build-user-object [user]
  (fn [sel & args]
    (condp = sel
        :username (:username user)
        :auth-check (= (:password user)
                       (first args))
        :email    (:email user)
        :skills   (:skills user)
        :friends  (:friends user)
        :groups   (:groups user)
        :id       (db/id user)
        :raw      user
        :temp-groups (:temp-groups user))))

(defn create-user
  "Create a user and insert into the database"
  [username password email skills friends groups temp-groups]
  (do
    (let [user (db/insert-user {:username username :password password :email email :skills skills :friends friends :groups groups :temp-groups temp-groups})]
      (build-user-object user))))

(defn find-user-by-username
  "User search function"
  [username]
  (build-user-object (db/find-user-by-username username)))

(defn find-user-by-email
  "User search function"
  [email]
  (build-user-object (db/find-user-by-email email)))

(defn find-user-by-id
  "User search function"
  [id]
  (build-user-object (db/find-user-by-id id)))

(defn update-user-friends
  "Add user ids to their friends list"
  [user friends]
  (build-user-object (db/update-user-friends user friends)))

(defn get-friends
  "Find all a users friends"
  [user]
  (build-user-object (apply find-user-by-id (:friends user))))
