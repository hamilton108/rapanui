{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE StrictData #-}

module Rapanui.Critters.OptionSale where

import Data.Aeson (FromJSON (..))
import GHC.Generics (Generic)
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
