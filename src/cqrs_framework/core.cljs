(ns cqrs-framework.core
  (:require [datascript.core :as d]
            [cqrs-framework.framework :as f]
            [cqrs-framework.view :as v]
            [cqrs-framework.seed :as seed]))

(enable-console-print!)

(def schema
  {:db/ident {:db/unique :db.unique/identity}})

(defonce conn
  (d/create-conn schema))

(defonce load
  (d/transact! conn seed/data))

(defonce app
  (f/mount (js/document.getElementById "app")
           conn
           #'v/render))

(defn on-js-reload []
  (f/render app))
