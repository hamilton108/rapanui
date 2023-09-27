{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE StrictData #-}

module Rapanui.Critters.OptionSale where

import Data.Aeson (FromJSON (..))
import GHC.Generics (Generic)
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
