{-# LANGUAGE DeriveGeneric #-}

module Rapanui.Critters.AcceptRule where

import Data.Aeson (FromJSON(..))
                   
import GHC.Generics (Generic)                             

import Rapanui.Common
  ( Oid(..)
  , Pid(..)
  , Cid(..)
  , Rtyp(..)
  )

data AcceptRule = 
  AcceptRule 
  { oid :: Oid
  , pid :: Pid
  , cid :: Cid
  , rtyp :: Rtyp 
  , value :: Double
  , active :: Bool
  }
  deriving (Eq,Show,Generic)

instance FromJSON AcceptRule 