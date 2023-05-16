{-# LANGUAGE OverloadedStrings #-}

module Rapanui.CalendarUtil where

import Data.List.Split
  ( splitOn
  )
import qualified Data.Time.Calendar as Calendar
import qualified Data.Time.Clock as Clock
import qualified Data.Time.LocalTime as LT
import qualified Data.Time.Zones as Z
import Data.Time.LocalTime 
  ( LocalTime(..)
  , TimeOfDay(..)
  )
import Data.Time.Clock.POSIX
  ( POSIXTime
  , getPOSIXTime
  , posixSecondsToUTCTime
  , utcTimeToPOSIXSeconds
  )
import Data.Time.Format
  ( defaultTimeLocale
  , formatTime
  )
import Rapanui.Common
  ( Iso8601 (..)
  , PosixTimeInt (..)
  , TimeInfo (..)
  , MarketOpen(..)
  , MarketClose(..)
  )


-- defaultDiffTime :: Clock.DiffTime
-- defaultDiffTime = Clock.secondsToDiffTime 0

-- dayToUnixTime :: Calendar.Day -> POSIXTime
-- dayToUnixTime d =
--   let dx = Clock.UTCTime d defaultDiffTime in utcTimeToPOSIXSeconds dx

-- unixTimeToInt :: POSIXTime -> Int
-- unixTimeToInt unixTime = floor $ toRational unixTime

-- strToUnixTime :: String -> POSIXTime
-- strToUnixTime s =
--   let x = read s :: Integer in fromInteger x :: POSIXTime

-- today :: IO Calendar.Day
-- today = Clock.getCurrentTime >>= \now -> pure $ Clock.utctDay now

-- currentTimeInfo :: IO TimeInfo
-- currentTimeInfo =
--   getPOSIXTime >>= \t ->
--     let
--       pt = round t :: Int
--       isot = formatTime defaultTimeLocale "%FT%T" (posixSecondsToUTCTime t)
--     in
--       pure $ TimeInfo (PosixTimeInt pt) (Iso8601 isot)


-- today >>= \td -> 
--   LocalTime td (LT.timeOfDay 9 20 0)
--   myz >>= \zz -> pure (Z.localTimeToUTCTZ zz today)

myTimeZone :: LT.TimeZone
myTimeZone = LT.TimeZone 120 True "LFS"

curUTCTime :: IO Clock.UTCTime 
curUTCTime = 
  getPOSIXTime >>= \t ->
    let
      pt = round t :: Int
    in 
      pure $ posixSecondsToUTCTime t

asLocalTimeOfDay :: IO TimeOfDay 
asLocalTimeOfDay = 
  curUTCTime >>= \ct -> 
    let 
      lt = LT.utcToLocalTime myTimeZone ct
    in
      pure $ LT.localTimeOfDay lt


strToTimeOfDay :: String -> TimeOfDay 
strToTimeOfDay s = 
  let 
    ss = splitOn ":" s
    s1 = read (head ss) :: Int 
    s2 = read (head $ tail ss) :: Int
  in
    TimeOfDay s1 s2 0 

timeOfDayPassed :: TimeOfDay -> IO Bool
timeOfDayPassed t =
  asLocalTimeOfDay >>= \ct -> 
    pure $ ct > t

-- marketOpenClose :: MarketOpen -> MarketClose -> MarketOpenClose
-- marketOpenClose (MarketOpen mo) (MarketClose mc) = 
--   MarketOpenClose (strToTimeOfDay mo) (strToTimeOfDay mc)

tt = TimeOfDay 13 51 0
