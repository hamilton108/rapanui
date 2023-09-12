module Rapanui.OptionSale.OptionSaleItem where

import Rapanui.Common
  ( Buy
  , Cid
  )

data SalePayload = SalePayload
  { critterId :: Cid 
  , price :: Buy
  }
  deriving (Eq, Show)

data OptionSale
  = NotActive
  | NoSale
  | Sale SalePayload
  deriving (Eq, Show)
