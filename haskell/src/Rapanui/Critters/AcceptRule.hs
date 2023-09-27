{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE NamedFieldPuns #-}

module Rapanui.Critters.AcceptRule where

import Data.Aeson (FromJSON (..))
import GHC.Generics (Generic)
import Rapanui.Common
  ( Bid (..)
  , Cid
  , Oid
  , Pid
  , Rtyp (..)
  , Ask(..)
  )
import Rapanui.OptionSale.OptionSaleItem
  ( OptionSale (..)
  , SalePayload (..)
  )
import Rapanui.StockOption
  ( StockOption (..)
  , StockOptionItem (..)
  )

data AcceptRule = AcceptRule
  { oid :: Oid
  , pid :: Pid
  , cid :: Cid
  , rtyp :: Rtyp
  , value :: Double
  , active :: Bool
  }
  deriving (Eq, Show, Generic)

instance FromJSON AcceptRule

apply' :: Ask -> StockOption -> Cid -> Rtyp -> Bid -> OptionSale
apply'
  (Ask s)
  (StockOption{option = StockOptionItem{bid = (Bid b2)}})
  c
  (Rtyp rt)
  (Bid value) =
    case rt of
      7 ->
        let
          diffFromBought = s - b2
        in
          if diffFromBought > value
            then Sale (SalePayload c (Bid b2))
            else NoSale
      _ -> NoSale

-- case rt of
--   7 ->
--     if b > bid then
--       NoSale
--     else
--       NoSale
--   _ -> NoSale

apply :: Ask -> StockOption -> AcceptRule -> OptionSale
apply s o AcceptRule{rtyp, value, active, cid} =
  if active == False
    then NoSale
    else apply' s o cid rtyp (Bid value)

--  oid |                   description
-- -----+--------------------------------------------------
--    1 | Diff from watermark
--    7 | Diff from bought
--    6 | Option price roof (valid if below option price)
--    5 | Option price floor (valid if above option price)
--    4 | Stock price roof (valid if below stock price)
--    3 | Stock price floor (valid if above stock price)
--    2 | Diff from watermark percent
--    8 | Composite
--    9 | Gradient diff from watermark
