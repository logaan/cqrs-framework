(ns cqrs-framework.view
  (:require [datascript.core :as d]
            [cqrs-framework.controller :as c]
            [cqrs-framework.elements :refer [div ul li form input]]))

(defn tasks [db]
  (map first
       (d/q '[:find (pull ?e [*])
              :where [?e :type :task]]
            db)))

(defn app-data [db]
  (d/entity db '[:db/ident :app]))

(defn render [app db]
  (let [{:keys [new-task]} (app-data db)]
    (div {}
         (ul {}
             (for [{:keys [description]} (tasks db)]
               (li {} description)))
         (form {:onsubmit (c/insert-new-task app)}
               (input {:type     "text"
                       :value    new-task
                       :onchange (c/update-new-task app)})
               (input {:type "submit" :value "Add" })))))
