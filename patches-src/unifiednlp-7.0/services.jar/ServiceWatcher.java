/*
 * Copyright (C) 2015 Marvin W <https://github.com/mar-v-in>
 * Copyright (C) 2016 Lanchon <https://github.com/Lanchon>
 *
 * This is Marvin's work converted to DexPatcher patches by Lanchon.
 *
 *      https://gerrit.omnirom.org/#/c/14898/
 *      https://gerrit.omnirom.org/#/c/14899/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server;

//import android.annotation.Nullable;
//import android.content.BroadcastReceiver;
//import android.content.ComponentName;
import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.ServiceConnection;
//import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
//import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
//import android.content.res.Resources;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.UserHandle;
import android.util.Log;
//import android.util.Slog;

//import com.android.internal.annotations.GuardedBy;
//import com.android.internal.content.PackageMonitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
//import java.util.Objects;

import lanchon.dexpatcher.annotation.*;

@DexEdit(onlyEditMembers = true)
public class ServiceWatcher /* implements ServiceConnection */ {

    @DexReplace
    public static ArrayList<HashSet<Signature>> getSignatureSets(Context context,
            List<String> initialPackageNames) {
        PackageManager pm = context.getPackageManager();
        ArrayList<HashSet<Signature>> sigSets = new ArrayList<HashSet<Signature>>();
        for (int i = 0, size = initialPackageNames.size(); i < size; i++) {
            String pkg = initialPackageNames.get(i);
            try {
                HashSet<Signature> set = new HashSet<Signature>();
                Signature[] sigs = pm.getPackageInfo(pkg, PackageManager.GET_SIGNATURES).signatures;
                set.addAll(Arrays.asList(sigs));
                sigSets.add(set);
            } catch (NameNotFoundException e) {
                Log.w("ServiceWatcher", pkg + " not found");
            }
        }
        return sigSets;
    }

    @DexIgnore
    private ServiceWatcher() { throw null; }

}
