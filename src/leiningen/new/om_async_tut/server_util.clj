(ns {{sanitized}}.util
  (:require [datomic.api :as d]
            [clojure.java.io :as io]
            [clojure.edn :as edn])
  (:import datomic.Util))

(def uri "datomic:free://localhost:4334/{{sanitized}}")

(defn read-all [f]
  (Util/readAll (io/reader f)))

(defn transact-all [conn f]
  (doseq [txd (read-all f)]
    (d/transact conn txd))
  :done)

(defn create-db []
  (d/create-database uri))

(defn get-conn []
  (d/connect uri))

(defn load-schema []
  (transact-all (get-conn) (io/resource "data/schema.edn")))

(defn load-data []
  (transact-all (get-conn) (io/resource "data/initial.edn")))
