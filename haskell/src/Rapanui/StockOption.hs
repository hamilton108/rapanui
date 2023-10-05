{-# LANGUAGE DeriveGeneric #-}

module Rapanui.StockOption where

import Data.Aeson (FromJSON (..))
import GHC.Generics (Generic)

import Rapanui.Common
  ( Bid (..)
  , Ask (..)
  , Status(..)
  , Msg(..)
  )

data StockOptionItem = StockOptionItem
  { bid :: Bid
  , ask :: Ask
  }
  deriving (Eq, Show, Generic)

data StockOption = StockOption
  { option :: StockOptionItem
  , status :: Status
  , msg :: Msg
  }
  deriving (Eq, Show, Generic)

instance FromJSON StockOptionItem

instance FromJSON StockOption

createStockOption :: Bid -> Ask -> Status -> Msg -> StockOption
createStockOption bid ask status msg =
  let
    item = StockOptionItem bid ask 
  in
    StockOption item status msg

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
--     "ivAsk": 0.1375,
--     "ask": 0.6,
--     "bid": 0.0,
--     "ivBid": 0.05,
--     "ot": 1,
--     "x": 528.02
--   }
-- }
