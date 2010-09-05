(ns small-jobs.users)

(defn create-user [username email skills friends groups temp-groups]
  {:username username :email email :skills skills :friends friends :groups groups :temp-groups temp-groups})

