(ns cqrs-framework.core
  (:require [datascript.core :as d]
            [cqrs-framework.controller :as c]
            [cqrs-framework.framework :as f]
            [cljsjs.virtual-dom]))

(def h js/virtualDom.h)

(enable-console-print!)

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

(def schema
  {:db/ident {:db/unique :db.unique/identity}})

(defonce conn
  (d/create-conn schema))

(defonce load
  (d/transact! conn [{:type        :task
                      :description "Buy milk"
                      :complete    false}
                     {:type        :task
                      :description "Eat curry"
                      :complete    false}
                     {:db/ident :app
                      :new-task "Pat kittens"}]))

(defonce app
  (f/mount (js/document.getElementById "app")
           conn
           #'render))

(defn on-js-reload []
  (f/render app))
