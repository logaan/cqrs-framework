(ns cqrs-framework.controller
  (:require [cqrs-framework.framework :as f]
            [datascript.core :as d]))

(defprotocol Todo
  (update-new-task [app])
  (insert-new-task [app]))

(defn set-new-task [conn value]
  (d/transact! conn [{:db/id     [:db/ident :app]
                      :new-task value}]))

(extend-type f/App
  Todo
  (update-new-task [{:keys [state]}]
    (fn [event]
      (set-new-task state js/event.target.value)))
  (insert-new-task [{:keys [state]}]
    (fn [event]
      (js/event.preventDefault)
      (let [{:keys [new-task]} (d/entity @state '[:db/ident :app])]
        (d/transact! state [{:type        :task
                             :description new-task
                             :complete    false}
                            {:db/id    [:db/ident :app]
                             :new-task ""}])))))
