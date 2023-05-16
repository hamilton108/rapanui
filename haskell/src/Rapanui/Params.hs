{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE StrictData #-}

module Rapanui.Params where

import qualified Data.Text as T

import Options.Applicative
  ( Parser
  , fullDesc
  , helper
  , info
  , progDesc
  , showDefault
  , (<**>)
  )
import Options.Applicative.Builder
  ( auto
  , help
  , long
  , option
  , short
  , strOption
  , value
  --, strArgument
  --, metavar
  )
import Options.Applicative.Extra (execParser)
import Rapanui.Common
  ( CritterType (..)
  , Env (..)
  , NordnetHost (..)
  , NordnetPort (..)
  , MarketOpen(..)
  , MarketClose(..)
  )
import qualified Rapanui.CalendarUtil as C

seconds2micro :: Int -> Int
seconds2micro ms = ms * 1000000

minutes2micro :: Int -> Int
minutes2micro ms = 60 * seconds2micro ms

defaultHost :: String
defaultHost = "localhost"

defaultPort :: Int
defaultPort = 8082

defaultOpen :: String
defaultOpen = "09:20"

defaultClose :: String
defaultClose = "11:06"
--defaultClose = "17:00"

defaultInterval :: Int
defaultInterval = 2 -- 60 * 5

data Params = Params
  { critterType :: String
  , nordnetHost :: String
  , nordnetPort :: Int
  , marketOpen :: String
  , marketClose :: String
  , interval :: Int
  }
  deriving (Show)

mkParams :: Parser Params
mkParams =
  Params
    -- <$> strArgument (metavar "CRITTER" <> help "Critter type, 11: test, 4: real money")
    <$> strOption
      ( long "critter"
          <> short 'c'
          <> help "Critters (paper trade: 11, real: 4)"
          <> value "11"
          <> showDefault
      )
    <*> strOption
      ( long "host"
          <> short 'q'
          <> help "Nordnet service host"
          <> value defaultHost
          <> showDefault
      )
    <*> option
      auto
      ( long "port"
          <> short 'p'
          <> help "Nordnet service port"
          <> value defaultPort
          <> showDefault
      )
    <*> strOption
      ( long "market-open"
          <> short 'o'
          <> help "Market opens"
          <> value defaultOpen
          <> showDefault
      )
    <*> strOption
      ( long "market-close"
          <> short 'x'
          <> help "Market closes"
          <> value defaultClose
          <> showDefault
      )
    <*> option
      auto
      ( long "interval"
          <> short 'i'
          <> help "Check time interval (seconds)"
          <> value defaultInterval
          <> showDefault
      )

cmdLineParser :: IO Params
cmdLineParser =
  let
    opts = info (mkParams <**> helper) (fullDesc <> progDesc "Rapanui")
  in
    execParser opts

params2env :: Params -> Env
params2env p =
  Env
    ( NordnetHost
        (T.pack $ nordnetHost p)
    )
    (NordnetPort $ nordnetPort p)
    ( CritterType (T.pack $ critterType p)
    )
    (MarketOpen (C.strToTimeOfDay $ marketOpen p))
    (MarketClose (C.strToTimeOfDay $ marketClose p))
    (seconds2micro $ interval p)
