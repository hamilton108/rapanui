module Rapanui.OptionSale.OptionSaleItem where

import Rapanui.Common
  ( Bid
  , Cid
  )

data SalePayload = SalePayload
  { critterId :: Cid 
  , price :: Bid
  }
  deriving (Eq, Show)

data OptionSale
  = NotActive
  | NoSale
  | Sale SalePayload
  deriving (Eq, Show)
