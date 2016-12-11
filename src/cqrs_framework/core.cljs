(ns cqrs-framework.core
  (:require [datascript.core :as d]
            [cqrs-framework.framework :as f]
            [cqrs-framework.view :as v]
            [cqrs-framework.seed :as seed]))

(enable-console-print!)

(def schema
  {:db/ident {:db/unique :db.unique/identity}})

(defonce app
  (let [conn      (d/create-conn schema)
        container (js/document.getElementById "app")]
    (d/transact! conn seed/data)
    (f/mount container conn #'v/render)))

(defn on-js-reload []
  (f/render app))
