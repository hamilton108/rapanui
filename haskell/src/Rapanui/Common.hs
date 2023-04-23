{-# LANGUAGE DeriveGeneric #-}
module Rapanui.Common where

import GHC.Generics (Generic)                             
import Data.Aeson (FromJSON(..))

newtype OptionTicker = OptionTicker String deriving (Eq,Show,Generic)

-- newtype StockTicker = StockTicker String deriving (Eq,Show,Generic)


newtype Oid = Oid Int deriving (Eq,Show,Generic)

newtype Pid = Pid Int deriving (Eq,Show,Generic)

newtype Cid = Cid Int deriving (Eq,Show,Generic)

newtype Rtyp = Rtyp Int deriving (Eq,Show,Generic)


instance FromJSON OptionTicker 

instance FromJSON Oid 
instance FromJSON Pid 
instance FromJSON Cid 
instance FromJSON Rtyp