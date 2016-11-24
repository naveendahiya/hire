(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('calendar-event', {
            parent: 'entity',
            url: '/calendar-event',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CalendarEvents'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/calendar-event/calendar-events.html',
                    controller: 'CalendarEventController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('calendar-event-detail', {
            parent: 'entity',
            url: '/calendar-event/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CalendarEvent'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/calendar-event/calendar-event-detail.html',
                    controller: 'CalendarEventDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CalendarEvent', function($stateParams, CalendarEvent) {
                    return CalendarEvent.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'calendar-event',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('calendar-event-detail.edit', {
            parent: 'calendar-event-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendar-event/calendar-event-dialog.html',
                    controller: 'CalendarEventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CalendarEvent', function(CalendarEvent) {
                            return CalendarEvent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calendar-event.new', {
            parent: 'calendar-event',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendar-event/calendar-event-dialog.html',
                    controller: 'CalendarEventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                calendarEventId: null,
                                type: null,
                                date: null,
                                title: null,
                                allDay: null,
                                dataItemId: null,
                                dataItemType: null,
                                enteredBy: null,
                                dateCreated: null,
                                dateModified: null,
                                siteId: null,
                                joborderId: null,
                                description: null,
                                duration: null,
                                reminderEnabled: null,
                                reminderEmail: null,
                                reminderTime: null,
                                publicV: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('calendar-event', null, { reload: 'calendar-event' });
                }, function() {
                    $state.go('calendar-event');
                });
            }]
        })
        .state('calendar-event.edit', {
            parent: 'calendar-event',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendar-event/calendar-event-dialog.html',
                    controller: 'CalendarEventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CalendarEvent', function(CalendarEvent) {
                            return CalendarEvent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('calendar-event', null, { reload: 'calendar-event' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calendar-event.delete', {
            parent: 'calendar-event',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendar-event/calendar-event-delete-dialog.html',
                    controller: 'CalendarEventDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CalendarEvent', function(CalendarEvent) {
                            return CalendarEvent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('calendar-event', null, { reload: 'calendar-event' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
