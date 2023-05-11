{-# LANGUAGE DeriveGeneric #-}

module Rapanui.StockOption where

import Data.Aeson (FromJSON (..))
import GHC.Generics (Generic)

import Rapanui.Common
  ( Buy (..)
  , Sell (..)
  )

data StockOptionItem = StockOptionItem
  { buy :: Buy
  , sell :: Sell
  }
  deriving (Eq, Show, Generic)

data StockOption = StockOption
  { option :: StockOptionItem
  }
  deriving (Eq, Show, Generic)

instance FromJSON StockOptionItem

instance FromJSON StockOption

createStockOption :: Buy -> Sell -> StockOption
createStockOption buy sell =
  let
    item = StockOptionItem buy sell
  in
    StockOption item

-- {
--   "stock-price": {
--     "h": 458.9,
--     "l": 450.6,
--     "c": 452.6,
--     "o": 452.0,
--     "unix-time": 1683028920000
--   },
--   "option": {
--     "brEven": 0.0,
--     "expiry": "2023-01-20",
--     "ticker": "YAR3A528.02X",
--     "days": 117,
--     "ivSell": 0.1375,
--     "sell": 0.6,
--     "buy": 0.0,
--     "ivBuy": 0.05,
--     "ot": 1,
--     "x": 528.02
--   }
-- }
