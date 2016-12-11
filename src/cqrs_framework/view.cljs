(ns cqrs-framework.view
  (:require [datascript.core :as d]
            [cqrs-framework.controller :as c]
            [cljsjs.virtual-dom]))

(def h js/virtualDom.h)

(defn tasks [db]
  (map first
       (d/q '[:find (pull ?e [*])
              :where [?e :type :task]]
            db)))

(defn app-data [db]
  (d/entity db '[:db/ident :app]))

(defn render [app db]
  (let [{:keys [new-task]} (app-data db)]
    (h "div" #js {}
       #js [(h "ul" #js {}
               (clj->js
                (for [{:keys [description]} (tasks db)]
                  (h "li" #js {} description))))
            (h "form" #js {:onsubmit (c/insert-new-task app)}
               #js [(h "input" #js {:type     "text"
                                    :value    new-task
                                    :onchange (c/update-new-task app)})
                    (h "input" #js {:type "submit" :value "Add" })])])))
